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
        
        // *** Listen to keyboard show / hide ***
        NotificationCenter.default.addObserver(self, selector: #selector(keyboardWillChangeFrame), name: NSNotification.Name.UIKeyboardWillChangeFrame, object: nil)
        
        self.title = self.chat.ad_title
        
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
            if #available(iOS 11, *) {
                if keyboardHeight > 0 {
                    keyboardHeight = keyboardHeight - view.safeAreaInsets.bottom
                }
            }
            textViewBottomConstraint.constant = keyboardHeight + 8
            view.layoutIfNeeded()
        }
    }
    
    
    @IBAction func sendAction(){
        self.textView.resignFirstResponder()
        let message = self.textView.text!
        
        if message.isEmpty{
            self.showErrorMessage(text: "Please type a message")
        }else{
            self.showLoading()
            Communication.shared.send_msg(ad_id: self.chat.ad_id.intValue, msg: message, callback: { (res) in
                
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
}
