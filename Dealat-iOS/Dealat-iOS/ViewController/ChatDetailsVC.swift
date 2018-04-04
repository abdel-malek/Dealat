//
//  ChatDetailsVC.swift
//  Dealat-iOS
//
//  Created by IOS Developer on 3/11/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import GrowingTextView
import IQKeyboardManagerSwift

class ChatDetailsVC: BaseVC {

    @IBOutlet weak var textView: GrowingTextView!
    @IBOutlet weak var textViewBottomConstraint: NSLayoutConstraint!
    @IBOutlet weak var tableView : UITableView!
    
    var chat = Chat()
    var messages = [Message]()

    override func viewDidLoad() {
        super.viewDidLoad()

        IQKeyboardManager.sharedManager().disabledToolbarClasses = [ChatDetailsVC.self]
        IQKeyboardManager.sharedManager().disabledTouchResignedClasses = [ChatDetailsVC.self]
        IQKeyboardManager.sharedManager().disabledDistanceHandlingClasses = [ChatDetailsVC.self]
        
        getData()
    }
    
    
    override func setupViews() {
        // *** Customize GrowingTextView ***
        textView.layer.cornerRadius = 4.0
        textView.delegate = self
        
        // *** Listen to keyboard show / hide ***
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillChangeFrame), name: NSNotification.Name.UIKeyboardWillChangeFrame, object: nil)
        
//        self.title = self.chat.ad_title
        
        let titleLbl = UILabel(frame: CGRect(x: 0, y: 8, width: 200, height: 200))
        titleLbl.text = self.chat.ad_title + "\n" + self.chat.seller_name
        titleLbl.textColor = UIColor.white
        titleLbl.numberOfLines = 0
//        titleLbl.font = Theme.Font.CenturyGothic
        titleLbl.textAlignment = .center
        titleLbl.minimumScaleFactor = 0.5
        self.navigationItem.titleView = titleLbl

        
        
        
        tableView.delegate = self
        tableView.dataSource = self
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100
        
        self.tableView.register(UINib.init(nibName: "MessageCell", bundle: nil), forCellReuseIdentifier: "MessageCell")

    }
    
    override func getRefreshing() {
        Communication.shared.get_chat_messages(chat: self.chat) { (res) in
            self.hideLoading()
            self.textView.text = nil
            self.messages = res
            self.tableView.reloadData()
            
            if !self.messages.isEmpty{
                self.tableView.scrollToRow(at: IndexPath.init(row: self.messages.count - 1, section: 0), at: UITableViewScrollPosition.bottom, animated: false)
            }
        }
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
    
    @objc private func keyboardWillChangeFrame(_ notification: Notification) {
        
        if let endFrame = (notification.userInfo?[UIKeyboardFrameEndUserInfoKey] as? NSValue)?.cgRectValue {
            var keyboardHeight = view.bounds.height - endFrame.origin.y
            
            print(keyboardHeight)
            
            if #available(iOS 11, *) {
                if keyboardHeight > 0 {
                    keyboardHeight = keyboardHeight - view.safeAreaInsets.bottom
                }
            }
            
            textViewBottomConstraint.constant = keyboardHeight
            view.layoutIfNeeded()
        }
    }
    
    func scrollViewWillBeginDragging(_ scrollView: UIScrollView) {
        print(scrollView.bounds.origin.y)
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == tableView{
//        let scrollViewHeight = scrollView.frame.size.height;
//        let scrollContentSizeHeight = scrollView.contentSize.height;
        let scrollOffset = scrollView.contentOffset.y;
        
        if scrollOffset < -80{
            self.textView.resignFirstResponder()
        }

//        if (scrollOffset == 0)
//        {
//            // then we are at the top
//        }
//        else if (scrollOffset + scrollViewHeight == scrollContentSizeHeight)
//        {
//            print("true")
//        }
        }
    }
    
    
    @IBAction func sendAction(){
//        self.textView.resignFirstResponder()
        let message = self.textView.text!
        
        if message.isEmpty{
            self.showErrorMessage(text: "Please type a message")
        }else{
//            self.showLoading()
            
            var chat_session_id : Int!
            if self.chat.chat_session_id != nil {
                chat_session_id = self.chat.chat_session_id.intValue
            }
            
            Communication.shared.send_msg(ad_id: self.chat.ad_id.intValue,chat_session_id: chat_session_id, msg: message, callback: { (res) in
                
                self.getRefreshing()
            })
            
        }
    }
    
    override func onTouch() {
        
    }
}

extension ChatDetailsVC : UITableViewDelegate, UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.messages.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MessageCell", for: indexPath) as! MessageCell
        
        cell.message = self.messages[indexPath.row]
        return cell
    }
    
}

extension ChatDetailsVC: GrowingTextViewDelegate {
    
    // *** Call layoutIfNeeded on superview for animation when changing height ***
    func textViewDidChangeHeight(_ textView: GrowingTextView, height: CGFloat) {
        UIView.animate(withDuration: 0.3, delay: 0.0, usingSpringWithDamping: 0.7, initialSpringVelocity: 0.7, options: [.curveLinear], animations: { () -> Void in
            self.view.layoutIfNeeded()
        }, completion: nil)
    }
    
    
    func textViewDidBeginEditing(_ textView: UITextView) {
        print("textViewDidBeginEditing")
        
        if !self.messages.isEmpty{
            self.tableView.scrollToRow(at: IndexPath.init(row: self.messages.count - 1, section: 0), at: UITableViewScrollPosition.bottom, animated: false)
        }
    }
}
