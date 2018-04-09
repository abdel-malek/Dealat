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
    }
    
    override func setupViews() {
        self.title = "Saved searches".localized
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100
        self.tableView.addSubview(ref)
    }
    
    override func getRefreshing() {
        Communication.shared.get_my_bookmarks { (res) in
            self.hideLoading()
            self.bookmarks = res
            self.tableView.reloadData()
        }
    }

    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.bookmarks.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        cell.textLabel?.text = self.bookmarks[indexPath.row].getName()
        
        return cell
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "MyFavoritesVC") as! MyFavoritesVC
        vc.type = 1
        vc.bookmark = self.bookmarks[indexPath.row]
        self.navigationController?.pushViewController(vc, animated: true)
    }

}
