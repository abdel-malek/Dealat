//
//  SavedSearchesVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/18/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class SavedSearchesVC: BaseVC, UITableViewDelegate,UITableViewDataSource {

    @IBOutlet weak var tableView : UITableView!
    var homeVC : HomeVC!
    var bookmarks = [UserBookmark]()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        getData()
        
        Provider.setScreenName("BookmarksActivity")

    }
    
    override func setupViews() {
        self.title = "Saved searches".localized
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100
        self.tableView.addSubview(ref)
        self.tableView.tableFooterView = UIView()
    }
    
    override func getRefreshing() {
        Communication.shared.get_my_bookmarks { (res) in
            self.hideLoading()
            self.bookmarks = res.filter({$0.query != nil})
            self.tableView.reloadData()
        }
    }

    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.bookmarks.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! BookmarkCell
        
//        cell.textLabel?.text = self.bookmarks[indexPath.row].getName()
        
        cell.selectionStyle = .none
        cell.parentVC = self
        cell.bookmark = self.bookmarks[indexPath.row]
        
        return cell
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
    }
    
    func showBookmark(_ bookmark : UserBookmark){
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "MyFavoritesVC") as! MyFavoritesVC
        vc.type = 1
        vc.bookmark = bookmark
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func deleteBookmark(_ bookmark : UserBookmark){
        let alert = UIAlertController.init(title: "Alert".localized, message: "DeleteBookmark".localized, preferredStyle: .alert)
        
        alert.addAction(UIAlertAction.init(title: "Ok", style: .default, handler: { (ac) in
            self.showLoading()
            Communication.shared.delete_bookmark(user_bookmark_id: bookmark.user_bookmark_id.intValue) { (res) in

                self.showErrorMessage(text: res.message)
                self.getRefreshing()
            }
        }))
        alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
        
        self.present(alert, animated: true, completion: nil)
    }

}
